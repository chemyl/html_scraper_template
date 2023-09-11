#### Task description:
* It is necessary to develop a utility program that is capable of parse html with the specified parameters.
* 
* **Example of how the program works**: 
* Find and save to XLS all info items in Ebay search by given keyword.
* Save Images in xls column
* After creating xls file should be available in resource output

#### Requirements:
* SpringBoot Application
* MVC architecture
* URL request data fetching
* JSoup
* Dockerized solution
* Dunamic keyword seting
* Development language java 11.

#### WorkFlow:
* Take html data by url GET request
* Create Jsoup Document Object from html response
* Convert Document to Pojo
* Store Data to new XLS file
* Set Key words and target URL to application.properties
* Start Spring Application
