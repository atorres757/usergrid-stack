
import sys
import psycopg2


def main():

    host = "XXXXX"
    dbname = "XXXXX"
    user = "XXXXX"
    password = "XXXXX"
    port = "XXXXX"
    stagingTable = "XXXXX"
    mainTable = "XXXXX"
    folderToImport = "s3://RedshiftTestLoading/redprac/"
    aws_secret_access_id = "XXXXX"
    aws_secret_access_key = "XXXXX"
    
    table_creation = '''create table newS3Import
        (id VARCHAR UNIQUE NOT NULL,
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
    
    connection = "host=" + host + " dbname=" + dbname + " user=" + user + " password=" + password + " port=" + port
    print "Establishing Connection to %s" % (connection)
    try:
        created_connection = psycopg2.connect(connection)
    except:
        print "Error establishing connection"
        exit()

    cursor = created_connection.cursor()
    try:
        cursor.execute(table_creation)
    except:
        print "problem with table creation"
        exit()
    
    try:
        cursor.execute("COPY "+stagingTable+" from '"+folderToImport+"' credentials 'aws_access_key_id="+aws_secret_access_id +";aws_secret_access_key="+aws_secret_access_key+"' IGNOREHEADER 2 EMPTYASNULL" )
    except:
        print "problem with copy"
        cursor.execute("drop * from " + stagingTable)
        exit()

    created_connection.commit()
    
    print "Connected!\n"

    try:
        cursor.execute("UPDATE "+mainTable+" SET id = s.id from " + stagingTable +
                   " s where "+mainTable+".created = s.created")
    except:
        print "problem with update"
        cursor.execute("drop * from " + stagingTable)
        exit()

    try:
        cursor.execute("INSERT INTO "+mainTable+" select s.* from " + stagingTable + " s LEFT JOIN "+mainTable+" n ON s.id = n.id where n.id IS NULL")
    except:
        print "problem with insert"
        cursor.execute("drop * from " + stagingTable)
        exit()


    cursor.execute("drop table " + stagingTable )
    
    created_connection.commit()
    print "Done!"

if __name__== "__main__":
    main()
