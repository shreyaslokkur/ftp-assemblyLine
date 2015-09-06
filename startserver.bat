@echo off

netstat -na | find "LISTENING" | find /C /I ":8080" > NUL
if %errorlevel%==1 (
	rm -rf E:\apache-tomcat-7.0.63-windows-x64\apache-tomcat-7.0.63\webapps\FTPAssemblyLine
	rm -rf E:\apache-tomcat-7.0.63-windows-x64\apache-tomcat-7.0.63\webapps\FTPAssemblyLine.war
	cp target\FTPAssemblyLine.war E:\apache-tomcat-7.0.63-windows-x64\apache-tomcat-7.0.63\webapps\
	E:\apache-tomcat-7.0.63-windows-x64\apache-tomcat-7.0.63\bin\startup.bat )

if %errorlevel%==0 (
	E:\apache-tomcat-7.0.63-windows-x64\apache-tomcat-7.0.63\bin\shutdown.bat
	rm -rf E:\apache-tomcat-7.0.63-windows-x64\apache-tomcat-7.0.63\webapps\FTPAssemblyLine
	rm -rf E:\apache-tomcat-7.0.63-windows-x64\apache-tomcat-7.0.63\webapps\FTPAssemblyLine.war
	cp target\FTPAssemblyLine.war E:\apache-tomcat-7.0.63-windows-x64\apache-tomcat-7.0.63\webapps\
	sleep 10
	E:\apache-tomcat-7.0.63-windows-x64\apache-tomcat-7.0.63\bin\startup.bat )