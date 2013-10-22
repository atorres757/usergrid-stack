#-----------------------------------------------------------------------------
# redshift_upsert.py
#
# The intention of this script is to have a script available that would 
# facilitate loading exported 
# data from s3 to amazon REDSHIFT for further processing. To reach this goal 
# the script follows Amazon's upsert procedure.
#
# One dependency: Psycog2 is needed to establish Postgres connection.
#
# To install on Mac with MacPorts install it via: 
#    $ sudo port install py27-psycopg2
#
# To install on Mac without MacPorts install with:
#    $ sudo easy_install pip
#    $ sudo pip install psycopg2
#
# Security Group setup: make sure your machine is allowed by the Security Group 
#
#-----------------------------------------------------------------------------

import sys
import psycopg2

def main():

    # setup and configuration
    host = "instaopsdw.c47fdstsx0ta.us-east-1.redshift.amazonaws.com"
    dbname = "instaopsdw"
    user = "instaopsdev"
    password = "Test1test"
    port = "5439"
    stagingTable = "stagingtable1"
    mainTable = "maintable1"
    folderToImport = "s3://RedshiftTestLoading/redprac/"
    aws_secret_access_id = "AKIAIQBZBLDEBTKPRXEQ"
    aws_secret_access_key = "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"

    # create table according current export list 
    table_creation = ''' (id VARCHAR UNIQUE NOT NULL,
        organization VARCHAR NOT NULL,
        application VARCHAR NOT NULL,
        UGtype VARCHAR  NOT NULL,
        created TIMESTAMP NOT NULL,
        modified TIMESTAMP NOT NULL,
        activity_category VARCHAR,
        activity_content VARCHAR,
        activity_title VARCHAR,
        activity_verb VARCHAR,
        asset_path VARCHAR,
        device_name VARCHAR,
        event_category VARCHAR,
        event_message VARCHAR,
        event_timestamp TIMESTAMP,
        folder_path VARCHAR,
        group_path VARCHAR,
        notification_canceled VARCHAR,
        notification_deliver VARCHAR,
        notification_errorMessage VARCHAR,
        notification_expire VARCHAR,
        notification_finished VARCHAR,
        notification_payloads VARCHAR,
        notification_queued VARCHAR,
        notification_started VARCHAR,
        notification_statistics VARCHAR,
        notifier_environment VARCHAR,
        notifier_provider VARCHAR,
        receipt_errorCode VARCHAR,
        receipt_errorMessage VARCHAR,
        receipt_notificationUUID VARCHAR,
        receipt_notifierId VARCHAR,
        receipt_payload VARCHAR,
        receipt_sent VARCHAR,
        user_email VARCHAR,
        user_name VARCHAR,
        user_picture VARCHAR,
        user_username VARCHAR
        );'''
    
    # sets up and connects to database
    connection = "host=" + host + " dbname=" + dbname + " user=" + user 
    connection = connection + " password=" + password + " port=" + port
    print "Establishing Connection to %s" % (connection)
    created_connection = psycopg2.connect(connection)

    cursor = created_connection.cursor()

    try:
        cursor.execute("create table " + mainTable + table_creation)
        print "Created " + mainTable
    except:
        print "Error creating " + mainTable + " perhaps it already exists"
        created_connection.rollback()

    try:
        cursor.execute("drop table " + stagingTable)
    except:
        print "Error dropping " + stagingTable
        created_connection.rollback()
    
    try:
        cursor.execute("create table " + stagingTable + table_creation)
        print "Created " + stagingTable
    except:
        print "Error creating " + stagingTable
        created_connection.rollback()
    
    # copy data from s3 to redshift staging table
    command = "COPY "+stagingTable+" from '"+folderToImport+"' credentials 'aws_access_key_id="
    command = command + aws_secret_access_id +";aws_secret_access_key="+aws_secret_access_key
    command = command +"' IGNOREHEADER 2 EMPTYASNULL" 
    print "Executing command: " + command
    cursor.execute(command)
   
    created_connection.commit()
    
    # run update of upsert command. 
    command = "UPDATE " + mainTable + " SET id = s.id from " + stagingTable + " s where "
    command = command + mainTable + ".created = s.created"
    print "Executing command: " + command
    cursor.execute(command)

    # inserts new values in staging table into your main table
    command  = "INSERT INTO "+mainTable+" select s.* from " + stagingTable + " s LEFT JOIN "
    command = command + mainTable + " n ON s.id = n.id where n.id IS NULL"
    print "Executing command: " + command
    cursor.execute(command)

    # drop staging table to reduce footprint
    cursor.execute("drop table " + stagingTable )
    
    created_connection.commit()
    print "Done!"

if __name__== "__main__":
    main()
