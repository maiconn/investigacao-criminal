cd %homedrivre%%homepath%
cd workspace\SIC\bin
set classpath=.
start java jpvm.jpvmDaemon
timeout /t 1
start java jpvm.jpvmConsole