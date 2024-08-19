# About
This Java library automates the creation of Internal Requisition Forms for the Research Software Engineering (RSE) team at Newcastle University. It is designed to streamline the management of project funds by integrating time tracking and financial data into a unified form that is submitted for internal processing.

# Built With
JAVA<br>
Maven

# Component
The library consists of several key classes:<br>
ClockifyAPI: Manages the retrieval of time-tracking data from Clockify.<br>
RSEAPI: Fetches project-related financial and descriptive data.<br>
MergeLists: Combines data from Clockify and RSE APIs.<br>
FillPDFForm: Automates the filling of the PDF forms with the merged data.<br>
ListPDFField: Maps the form fields to the data extracted and processed from the APIs.

# Prerequisites
Before you set up and run the library, ensure you have the following:<br>
Java JDK 8 or higher<br>
Apache Maven for dependency management<br>
Access to Clockify and RSE APIs<br>
Apache PDFBox library for handling PDF operations

# Getting Started<br>
1.Clone the repository:<br>
'git clone https://yourrepositoryurl.git<br>
cd your-project-directory'

2.Install Dependencies:<br>
Use Maven to install the required dependencies.<br>
'mvn install'

3.Configuration:<br>
Edit the config.properties file in the src/main/resources directory to include your API keys and other necessary configurations.<br>
'clockify.api.key=your_clockify_api_key<br>
rse.api.key=your_rse_api_key'

4.Set DateRange of year<br>
You can set the year in ClockifyAPI class, which is set as 2024 initially<br>
'LocalDateTime startDateTime = LocalDateTime.of(2024, month, 1, 0, 0, 0);<br>
LocalDateTime endDateTime = startDateTime.withDayOfMonth(startDateTime.getMonth().maxLength()).withHour(23).withMinute(59).withSecond(59);'

5.Change the path to save files<br>
You can find it in FillPDFForm class<br>
Here is the path where you save forms<br>
' String monthFolder = String.format("C:/Users/13016/Desktop/test/%02d", month);'<br>

Here is the path where the blank form is saved<br>
'PDDocument document = PDDocument.load(new File("C:/Users/13016/Desktop/Dissertation/ir-template.pdf"));'

6.Run the FillPDFForm class <br>
Run this class and you will find the form files in the folder

# Contributing<br>
Contributions to this project are welcome. Please fork the repository and submit a pull request with your enhancements. For major changes, please open an issue first to discuss what you would like to change.


