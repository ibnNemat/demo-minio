# Instructions for starting Minio

### Download MinIO:
You can download MinIO from the official website: https://min.io/download
Select the appropriate version for your operating system (Linux, Windows, macOS).

### Install MinIO:
Unzip the downloaded file if necessary. On Linux this can be done using the tar or unzip command.

### Start MinIO:
Go to the directory where the MinIO executable is located and run it using the command in the terminal.

#### For Windows:
bash
```
minio.exe server /path/to/data
```

#### For Linux or macOS:
bash
```
./minio server /path/to/data
```
Here /path/to/data is the directory where MinIO will store the data.

### Open the web interface:
Navigate your web browser to the URL provided (for example, http://127.0.0.1:9000).

#### Login to MinIO:
Use AccessKey and SecretKey to login. The default is 'minioadmin' for both fields unless you change them.

#### Then create a bucket. The bucket name must match the minio.bucketName in application.properties
