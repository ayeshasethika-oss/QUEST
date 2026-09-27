# QUEST Application Runner Script
$jdkBin = (Get-ChildItem -Path "C:\Users\ELCOT\tools\jdk" -Filter "java.exe" -Recurse).DirectoryName
$mvnBin = (Get-ChildItem -Path "C:\Users\ELCOT\tools\mvn" -Filter "mvn.cmd" -Recurse).DirectoryName

if ($jdkBin) {
    $env:JAVA_HOME = (Get-Item $jdkBin).Parent.FullName
    $env:Path = "$jdkBin;$mvnBin;" + $env:Path
    Write-Host "Configured JAVA_HOME: $env:JAVA_HOME" -ForegroundColor Green
}

Write-Host "Building QUEST Spring Boot Application..." -ForegroundColor Cyan
& "$mvnBin\mvn.cmd" clean package -DskipTests

if ($LASTEXITCODE -eq 0) {
    Write-Host "Build Successful! Launching QUEST Server on http://localhost:8080..." -ForegroundColor Green
    & "$jdkBin\java.exe" -jar target/quest-app-1.0.0.jar
} else {
    Write-Host "Build failed. Please check error logs." -ForegroundColor Red
}
