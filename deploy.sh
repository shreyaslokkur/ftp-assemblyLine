# Stop tomcat server
/cygdrive/e/apache-tomcat-7.0.63-windows-x64/apache-tomcat-7.0.63/bin/shutdown.bat

# Delete old war and folder
rm -rf /cygdrive/e/apache-tomcat-7.0.63-windows-x64/apache-tomcat-7.0.63/webapps/FTPAssemblyLine*

# Copy new war
cp target/FTPAssemblyLine.war /cygdrive/e/apache-tomcat-7.0.63-windows-x64/apache-tomcat-7.0.63/webapps/

# Start Tomcat
/cygdrive/e/apache-tomcat-7.0.63-windows-x64/apache-tomcat-7.0.63/bin/startup.bat
