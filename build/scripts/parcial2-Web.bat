@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem
@rem SPDX-License-Identifier: Apache-2.0
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  parcial2-Web startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and PARCIAL2_WEB_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH. 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME% 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\parcial2-Web-1.0-SNAPSHOT.jar;%APP_HOME%\lib\javalin-7.0.1.jar;%APP_HOME%\lib\thymeleaf-3.1.2.RELEASE.jar;%APP_HOME%\lib\jsoup-1.15.3.jar;%APP_HOME%\lib\hibernate-hikaricp-6.4.4.Final.jar;%APP_HOME%\lib\hibernate-core-6.4.4.Final.jar;%APP_HOME%\lib\h2-2.1.214.jar;%APP_HOME%\lib\jakarta.persistence-api-3.1.0.jar;%APP_HOME%\lib\HikariCP-5.1.0.jar;%APP_HOME%\lib\jackson-annotations-2.16.1.jar;%APP_HOME%\lib\jackson-core-2.16.1.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.16.1.jar;%APP_HOME%\lib\jackson-databind-2.16.1.jar;%APP_HOME%\lib\jasypt-1.9.3.jar;%APP_HOME%\lib\postgresql-42.7.3.jar;%APP_HOME%\lib\slf4j-simple-2.0.13.jar;%APP_HOME%\lib\javase-3.5.3.jar;%APP_HOME%\lib\core-3.5.3.jar;%APP_HOME%\lib\jetty-ee10-websocket-jetty-server-12.1.6.jar;%APP_HOME%\lib\jetty-ee10-websocket-servlet-12.1.6.jar;%APP_HOME%\lib\jetty-ee10-annotations-12.1.6.jar;%APP_HOME%\lib\jetty-ee10-plus-12.1.6.jar;%APP_HOME%\lib\jetty-ee10-webapp-12.1.6.jar;%APP_HOME%\lib\jetty-ee10-servlet-12.1.6.jar;%APP_HOME%\lib\jetty-plus-12.1.6.jar;%APP_HOME%\lib\jetty-security-12.1.6.jar;%APP_HOME%\lib\jetty-session-12.1.6.jar;%APP_HOME%\lib\jetty-websocket-jetty-server-12.1.6.jar;%APP_HOME%\lib\jetty-annotations-12.1.6.jar;%APP_HOME%\lib\jetty-websocket-core-server-12.1.6.jar;%APP_HOME%\lib\jetty-ee-webapp-12.1.6.jar;%APP_HOME%\lib\jetty-server-12.1.6.jar;%APP_HOME%\lib\jetty-websocket-jetty-common-12.1.6.jar;%APP_HOME%\lib\jetty-websocket-core-common-12.1.6.jar;%APP_HOME%\lib\jetty-http-12.1.6.jar;%APP_HOME%\lib\jetty-io-12.1.6.jar;%APP_HOME%\lib\jetty-jndi-12.1.6.jar;%APP_HOME%\lib\jetty-xml-12.1.6.jar;%APP_HOME%\lib\jetty-util-12.1.6.jar;%APP_HOME%\lib\slf4j-api-2.0.17.jar;%APP_HOME%\lib\kotlin-stdlib-2.2.20.jar;%APP_HOME%\lib\ognl-3.3.4.jar;%APP_HOME%\lib\attoparser-2.0.7.RELEASE.jar;%APP_HOME%\lib\unbescape-1.1.6.RELEASE.jar;%APP_HOME%\lib\jakarta.transaction-api-2.0.1.jar;%APP_HOME%\lib\jboss-logging-3.5.0.Final.jar;%APP_HOME%\lib\hibernate-commons-annotations-6.0.6.Final.jar;%APP_HOME%\lib\jandex-3.1.2.jar;%APP_HOME%\lib\classmate-1.5.1.jar;%APP_HOME%\lib\byte-buddy-1.14.11.jar;%APP_HOME%\lib\jaxb-runtime-4.0.2.jar;%APP_HOME%\lib\jaxb-core-4.0.2.jar;%APP_HOME%\lib\jakarta.xml.bind-api-4.0.0.jar;%APP_HOME%\lib\jakarta.inject-api-2.0.1.jar;%APP_HOME%\lib\antlr4-runtime-4.13.0.jar;%APP_HOME%\lib\checker-qual-3.42.0.jar;%APP_HOME%\lib\jcommander-1.82.jar;%APP_HOME%\lib\jai-imageio-core-1.4.0.jar;%APP_HOME%\lib\jakarta.servlet-api-6.0.0.jar;%APP_HOME%\lib\jetty-websocket-jetty-api-12.1.6.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\javassist-3.29.0-GA.jar;%APP_HOME%\lib\angus-activation-2.0.0.jar;%APP_HOME%\lib\jakarta.activation-api-2.1.1.jar;%APP_HOME%\lib\jakarta.interceptor-api-2.1.0.jar;%APP_HOME%\lib\jakarta.annotation-api-2.1.1.jar;%APP_HOME%\lib\asm-commons-9.9.1.jar;%APP_HOME%\lib\asm-tree-9.9.1.jar;%APP_HOME%\lib\asm-9.9.1.jar;%APP_HOME%\lib\txw2-4.0.2.jar;%APP_HOME%\lib\istack-commons-runtime-4.1.1.jar;%APP_HOME%\lib\jakarta.enterprise.cdi-api-4.0.1.jar;%APP_HOME%\lib\jakarta.enterprise.lang-model-4.0.1.jar


@rem Execute parcial2-Web
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %PARCIAL2_WEB_OPTS%  -classpath "%CLASSPATH%" com.codewithmosh.App %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable PARCIAL2_WEB_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%PARCIAL2_WEB_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
