#!/bin/bash
# Wait for SQL Server to start up
echo "Waiting for SQL Server to be available..."
for i in {1..60};
do
    /opt/mssql-tools18/bin/sqlcmd -S sqlserver -U sa -P "$MSSQL_SA_PASSWORD" -C -Q "SELECT 1" > /dev/null 2>&1
    if [ $? -eq 0 ]
    then
        echo "SQL Server is UP! Executing setup-databases.sql..."
        /opt/mssql-tools18/bin/sqlcmd -S sqlserver -U sa -P "$MSSQL_SA_PASSWORD" -C -i /setup-databases.sql
        echo "Database initialization complete."
        exit 0
    fi
    echo "SQL Server is still starting up, waiting 2 seconds... ($i/60)"
    sleep 2
done

echo "Error: SQL Server did not start within expected time."
exit 1
