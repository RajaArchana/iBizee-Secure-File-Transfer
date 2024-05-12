# iBizee-Secure-File-Transfer

# Introduction
This solutions mainly focus on transfering documents safely to another personal or a branch within the same company chain. The usefulness of this solution based on that the ousiders can not access these data without knowing the passwords and credentials. In such wise, the development of the project basically covers uploading and transferring files, generating public & private keys using RSA algorithm, encrypt files using public keys, decrypt files using private keys and lastly, unlocking access by matching variables. 

# Design
The designing part consists of serveral components. First conponent is the "Sender" who uploads files and choose a passowrd in the system. Additionally, inserting a description about the content that upload is an optional but identified as an important part in the component.

Seconds place goes to the process Encrypting files using the public key which generated using the RSA algorithm. This component also identified as a primary component of the whole solution.

Third place is uploading encrypted files into pre defined google drive location which is belongs to the same company. The account access has limited for Administration due to maintain the insurance.

Storing original files names & upload timestamp in the database is the fourth place of the components. It basically storing data as plain text since the final component use it as a reference of the validation process.

Fifth place also known as the key component of the solution which is storing private and public keys in a Blockchain service. This is for the secutiry and to keep a track of accessing and maintaning keys.

Download files from the cloud and decrypt using the keys which are stored in the Bloackchain service is the another component of the project. This component basically accesing to the Blockchain servive and access the keys securly for the decryption process.

Final component is the "Receiver" access the files which are decrepted and validated within the system.

# Implementation
The implementation part completes as two modules and one of them is the Backend and the another module is the Frontend. Java, Spring boot framework and the MySQL used for the development of the backend. For this, we use Intellij IDEA and XAMPP tools.

For the Frontend, Angular framework HTML, CSS and Typescript use to develop and  Visual Studio Code is used.

# Testing
Testing of the implementation mainly initiates as two stages. The first stage take place where the code is developing and integrating. A application called "Postman" used during this process. The second phase initiates when the development is 100% completed. During this phase use the Frontend to manipulate the process.


