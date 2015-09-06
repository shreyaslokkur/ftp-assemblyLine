RESULT=`netstat -na | grep '$2' | awk '{print $7}' | wc -l`
# Stop tomcat server if it is running
if [ $RESULT != 0 ]; then
  /cygdrive/e/apache-tomcat-7.0.63-windows-x64/apache-tomcat-7.0.63/bin/shutdown.bat
fi

DIRECTORY=/cygdrive/e/apache-tomcat-7.0.63-windows-x64/apache-tomcat-7.0.63/webapps/FTPAssemblyLine*
# Delete old war and folder
if [ -d "$DIRECTORY" ]; then
  # Control will enter here if $DIRECTORY exists.
  rm -rf "$DIRECTORY"
fi

DIRECTORY1=/cygdrive/e/apache-tomcat-7.0.63-windows-x64/apache-tomcat-7.0.63/webapps/ROOT
if [ -d "$DIRECTORY1" ]; then
  # Control will enter here if $DIRECTORY exists.
  rm -rf "$DIRECTORY1"
fi

# Copy new war
cp target/FTPAssemblyLine.war /cygdrive/e/apache-tomcat-7.0.63-windows-x64/apache-tomcat-7.0.63/webapps/

# Rename war file if it exists
FILE_STATUS=$([ -f /etc/hosts ] && echo 0 || echo 1 )
if [ $FILE_STATUS == 0 ]; then
  mv /cygdrive/e/apache-tomcat-7.0.63-windows-x64/apache-tomcat-7.0.63/webapps/FTPAssemblyLine.war /cygdrive/e/apache-tomcat-7.0.63-windows-x64/apache-tomcat-7.0.63/webapps/ROOT.war
fi

# Start Tomcat
/cygdrive/e/apache-tomcat-7.0.63-windows-x64/apache-tomcat-7.0.63/bin/startup.bat

TOMCAT_HOME=/cygdrive/e/apache-tomcat-7.0.63-windows-x64/apache-tomcat-7.0.63/

wget -O - http://localhost:8080/ >& /dev/null
if( test $? -eq 0 ) then
 exit 0
else
 exit 1
fi