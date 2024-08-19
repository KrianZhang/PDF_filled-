# About
This Java library automates the creation of Internal Requisition Forms for the Research Software Engineering (RSE) team at Newcastle University. It is designed to streamline the management of project funds by integrating time tracking and financial data into a unified form that is submitted for internal processing.

# Built With
JAVA
Maven

# Component
The library consists of several key classes:
ClockifyAPI: Manages the retrieval of time-tracking data from Clockify.
RSEAPI: Fetches project-related financial and descriptive data.
MergeLists: Combines data from Clockify and RSE APIs.
FillPDFForm: Automates the filling of the PDF forms with the merged data.
ListPDFField: Maps the form fields to the data extracted and processed from the APIs.

# Prerequisites
Before you set up and run the library, ensure you have the following:
Java JDK 8 or higher
Apache Maven for dependency management
Access to Clockify and RSE APIs
Apache PDFBox library for handling PDF operations

# Getting Started
1.Clone the repository:
'git clone https://yourrepositoryurl.git
cd your-project-directory'

2.Install Dependencies:
Use Maven to install the required dependencies.
'mvn install'

3.Configuration:
Edit the config.properties file in the src/main/resources directory to include your API keys and other necessary configurations.
'clockify.api.key=your_clockify_api_key
rse.api.key=your_rse_api_key'

4.Set DateRange of year
You can set the year in ClockifyAPI class, which is set as 2024 initially
'LocalDateTime startDateTime = LocalDateTime.of(2024, month, 1, 0, 0, 0);
LocalDateTime endDateTime = startDateTime.withDayOfMonth(startDateTime.getMonth().maxLength()).withHour(23).withMinute(59).withSecond(59);'

5.Change the path to save files
You can find it in FillPDFForm class
Here is the path where you save forms
' String monthFolder = String.format("C:/Users/13016/Desktop/test/%02d", month);'

Here is the path where the blank form is saved
'PDDocument document = PDDocument.load(new File("C:/Users/13016/Desktop/Dissertation/ir-template.pdf"));'

6.Run the FillPDFForm class 
Run this class and you will find the form files in the folder

# Contributing
Contributions to this project are welcome. Please fork the repository and submit a pull request with your enhancements. For major changes, please open an issue first to discuss what you would like to change.


