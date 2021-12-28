# LogFinder

## How to use
`java -jar LogFinder-jar-with-dependencies.jar <TargetFile> -f <filters> -p <pattern>`

- `-f <filters>   Filtering Strings`

- `-p <pattern>   Log Pattern (regex)`
### Example

`java -jar LogFinder-jar-with-dependencies.jar sample.log -f  808a424a6969ec1e5cb6126bc5a00 -p "\[\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}\.\d{3}](.|\n)*" > output.log`
