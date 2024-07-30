This project helps us to extract data from KAFKA topics with the help of FLINK live streaming and writes the data in Elasticsearch with transformations for downstream usage.

1. Producing Data with kafka in sales-topic ->
   
![image](https://github.com/user-attachments/assets/67fd0118-a596-48af-b808-271794fcc92a)

3. Two Kafka servers are running each with 1 partition and replication factor is also 1 ->
   
![image](https://github.com/user-attachments/assets/7a175225-991e-40d0-b165-1eae08575530)

5. Submitting flink job ->
   
![image](https://github.com/user-attachments/assets/7442d58a-fed5-4229-ad6c-5f5a43c7d93d)

![image](https://github.com/user-attachments/assets/14f5a6cc-d555-4f92-8b82-9cb7adabc661)


5. As per requirement, we are mapping data with different use cases. Example. SalesPerProduct, SalesPerDay, SalesPerCategory ->
   
![image](https://github.com/user-attachments/assets/f9a25837-5854-41dd-8a0a-0e4b87120ad4)

7. Writing transactions data in Elasticsearch datastore ->
   
![image](https://github.com/user-attachments/assets/053a2351-f75c-4e27-961e-1a25f1c519d7)


