The existing implementation of maf data processing runs on prem. This attempt is to rewrite the entire data pipeline to run it on GCP taking advantage of the cloud native services like the google cloud composer.

The Google composer will have MAF loader DAG. The process initiation is a manual activity that will be performed once we get a notification mail from oneTru team on the MAF data asset, the bucket path needs to be updated in Airflow admin variables before we start the process. Composer  execution based on a pub/sub event from oneTru will be taken up later. The process will read the files from the GCS bucket and spawn a DAG Task for each file. At the max three tasks will be taken up in one worker node and the worker nodes can scale if there are more than 3 files. The max worker node is configured to 10. The DAG calls the java process which reads the file using AvroParquetReader does the transformation for each field as needed and converted to protobuf format. This protobuf format is saved into Aerospike as blob data.

The Data processor acts as a thread and gets the generic record from BlockingQueue. The number of threads for each file processing unit is 200. This will be optimized based on the tests.

New Architecture

Sets and bins that are getting updated:-
pid

Columns of pid:-

PK  :- (key for the table)

address. :- (known column, same as Address in AddressAccessor)

bizinfoidx :- (unknown column, did not find any usage in ETL C# code)

commondemohh :- (unknown column, did not find any usage in ETL C# code)

indfraudidx :- (unknown column, did not find any usage in ETL C# code)

indmarketidx :- (unknown column, did not find any usage in ETL C# code)

indmatchidx :- (unknown column, did not find any usage in ETL C# code)

 

dpc 

Columns of dpc:-

PK :- (key for the table)

addressidx  :- (the column is present in ETL C# code but there are multiple tables using the same column name, same column is in defaultpid, pidsha1)

property :- (unknown column, did not find any usage in ETL C# code)

 

defaultpid

Columns of defaultpid:-

PK :- (key for the table)

addressidx :- (the column is present in ETL C# code but there are multiple tables using the same column name, same column is in dpc, pidsha1)

 

pidsha1

Columns of pidsha1:-

PK :- (key for the table)

addressidx :- (the column is present in ETL C# code but there are multiple tables using the same column name, same column is in dpc, defaultpid)

 

zip5

Columns of zip5:-

PK :- (key for the table)

summary :- (known column, same as zip5Summary in AddressAccessor, the parse function is able to convert its values)

 

zip9

Columns of zip9:-

PK :- (key for the table)

indfraudidx :- (unknown column, did not find any usage in ETL C# code)

indmarketidx :- (unknown column, did not find any usage in ETL C# code)

indmatchidx :- (unknown column, did not find any usage in ETL C# code)

summary :- (known column, same as zip9Summary in AddressAccessor, the parse function is able to convert its values but the fields were not coming correct)


Overview:- The app converts parquet files from gcs bucket to Address format utilising protobuf format, which creates a AddressAccessor.Address object which is then processed through a business logic. After the business logic is applied the object is converted to byte array and saved to aerospike.

Technologies used:-

Google cloud composer

Google cloud storage

Python

Java

Aerospike

CloudSQL(MySQL)

The whole process is divided into two processes:-

First process is a python code which will read the file from gcs bucket, insert a row of data about the file to MySQL db and push that file to java subprocess(which is another process) to convert the data, after the file is processed another row will be inserted showing the success or failure of the file. After all the files are processed an email notification will be sent.

The java process which is a subprocess will be called on every file, this process will convert the parquet file, take every generic record convert every generic record using the business logic and save the converted generic record to aerospike as byte array. 

Java subprocess in detail:-
Flow:-

read parquet files → convert to GenericRecord → push the records to blocking queue → convert each record using business logic → convert the object to byte array → push to aerospike

The AddressAccessor is a class that have been compiled from protobuf file.

AddressAccessor class have different classes such as Address, AddressIndexRecord, AddressLoadRecord,Zip5Summary, Zip9Summary etc. These class have builders which are being used to create object of these classes. These objects are getting converted to byte array(the conversion of byte array is being done using tobyteArray() method from AddressAccessor class only).

Aerospike:-
The insertion to aerospike is happening through the put method which takes mutiple parameters which are:- 

put(WritePolicy writePolicy, key key, Bin blobBin);

1st parameter is null which is for WritePolicy of aerospike which will be discussed later on in this document, key is the key object Key key = new Key(namespace, setName, id);  that contains namespace name, set name and primary key for the table respectively, blobBin is the bin definition for the set, Bin blobBin = new Bin(columName, byteArray); here it is name of the bin and byteArray which is of type byte[].

 

Aerospike defined policies:-



      WritePolicy writePolicy = new WritePolicy();
//        expiration means to delete automatically, setting it as -1 means to never expire,default is 0(depends on ttl of server), ttl>0 is actual ttl in seconds
        writePolicy.expiration = 0;
//        maximum time for write operation including retries double of socketTimeout
        writePolicy.totalTimeout = 60000;
//update the record in db if the record with same key already exists
        writePolicy.recordExistsAction = RecordExistsAction.UPDATE;
        clientPolicy.maxConnsPerNode = 60000;

clientPolicy.maxConnsPerNode is maximum number of connection allowed per node.

Metrics:-
All the metrics related to performance is available on Airflow in the form of logs.The metrics can be analysed from there.

Execution time:

Open Screenshot 2024-12-27 at 5.07.46 PM.png

Worker stats:

Open Screenshot 2024-12-24 at 6.56.14 PM.png

 

Open Screenshot 2024-12-24 at 6.56.49 PM.png

 

Open Screenshot 2024-12-24 at 6.56.35 PM.png

Old transformation logic
The existing logic of Maf file transformation is as below

KEY:

M - Associated with MAF TABLE

NM - Not Associated with MAF TABLE

P – Matching with Corresponding PROTOBUF values NP – Not Matching with Corresponding PROTOBUF values

C – Conversions between data types exist

NC – No Conversions between data types exist

 PROCEDURE:

BASED ON BIT SLICING - MPNC

Input I = Reading 32 bits (4 bytes)

ALWAYS: Little Endian I

DpvConfirmCode => Slice 0 to 2 position bits

LacsLinkConfirmCode => Slice 3 to 4 position bits

AddressType => Slice 5 to 8 position bits

GovernmentBuildingIndicator => Slice 9 to 12 position bits

ResidenceOrBusinessIndicator => Slice 14 to 15 position bits

DuplicateIndicator => Slice 16 to 17 position bits

MatchPrecision => Slice 18 to 21 position bits

SingleHomeOfficeIndicator => Slice 22 to 25 position bits

IsBuilding => Slice 26 position bit

                  10) VacancyIndicator => Slice 27 to 28 position bits

                  11) mafNoStat => Slice 29 to 30 position bits (NOT CURRENTLY USED FIELD)

                  12) IsPrison => Slice 31 position bit

ALWAYS: Return the 0th byte of the resultant byte array as the output

Example Input: 10001001 11101001 10001101 10001011

                  |100|01|001 1|1101|0|01|10|0011|01 10|0|01|01|1

DpvConfirmCode => 100 => 00000100 => 4 => Case 4 => Corresponding Proto Value => Assign and Update Key, Value Pair

....

12) IsPrison => 1 => true => Assign and Update Key, Value Pair

 BASED ON BIT SLICING - MNPC

Input I = Reading 32 bits (4 bytes)

ALWAYS: Little Endian I

                  13) isAlternateRecord => 13th bit position

ALWAYS: Return the 0th byte as the result

Example Input: 10001001 11101|0|01 10001101 10001011

13) 0 => Convert to Boolean =>  false => Assign and Update Key, Value Pairs

 

BASED ON DIVISION - MNPNC

Input I= 32-bit Unsigned Integer

14) Latitude => I/1000000.0

15) Longitude => I/-1000000.0

Example Input: 33077013

14) Latitude => 33322119/1000000.0 => 33.322119

15) Longitude => -117092363/-1000000.0 =>-117.0924

BASED ON ONLY COPYING - MNPNC

SAME BYTE VALUES

Input I = Reading 8 bits (1 byte)

16) BizPhoneCount

17) ResPhoneCount

18) PublishedBizPhoneCount

19) PublishedResPhoneCount

20) BusinessE1Segment (is derived from be1_code)

21) StreetType

22) PublishedDualPhoneCount

Example Input: 00000101

16) BizPhoneCount => 5 => Assign and Update Key, Value Pair

...

22) PublishedDualPhoneCount => 5 => Assign and Update Key, Value Pair

SAME INTEGER VALUES

Input I = Reading 32-bit Integer Value

23) FirmName

24) StreetName

25) BuildingNumber

26) CityName (Change in-bit - from 32 to 16)

27) DefaultZip9

BASED ON ONLY COMPARING – MPNC

                  SAME BYTE VALUES

Input I = Reading 8 bits (1 byte)

28) E1MatchPrecision

29) CMRAIndicator

30) BusinessE1MatchPrecision

Example Input: 00000011

28) E1MatchPrecision => 3 => Case 3 => Corresponding Proto Value => Assign and Update Key, Value Pair

30) BusinessE1MatchPrecision => 3 => Case 3 => Corresponding Proto Value => Assign and Update Key, Value Pair

BASED ON CREATING NEW ATTRIBUTES- NMNPNC

SAME INTEGER VALUES

Input I = Reading 32-bit Integer Value

31) BuildingName
