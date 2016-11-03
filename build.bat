@ECHO OFF
@cls
ECHO === build JPedal ===
set MAVEN_OPTS=-Dfile.encoding=UTF-8 -Xmx512m
REM set MAVEN_OPTS=-Dmaven.test.skip.exec=true
call mvn -U -e clean install
pause
