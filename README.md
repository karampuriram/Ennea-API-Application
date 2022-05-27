# Ennea-API-Application
Ennea API Application

# Problem Description

The following statements apply to the columns in the csv.
●	A product name is identified by its code.
●	The product can be supplied or sold by multiple suppliers.
●	The batch, stock and deal relate to the inventory information regarding the product that the supplier has.


Create a web backend application with the following supported endpoints (API’s):
1)	API to accept a CSV file and ingest the data into a db. You can take it that a new csv added will be of the same format.
2)	Given a supplier id or name, list all the products that the supplier has with stock.
3)	Accept a search param for the above filter based on the product name.
4)	Accept a param for the above api, or create a new endpoint to only list out products that didn’t yet expire for that supplier or list of suppliers.
5)	Make the above api’s pageable.


## API

# Endpoint
    POST /upload
   ● Description:
        1) Store product records in Database or Copy product records from CSV file to Database.
        2) If the fields in the file are mismatched then the response entity is INTERNAL_SERVER_ERROR.
        3) Book1.csv is the csv file with all the fields. Book2.csv is the csv file with mismatched fields count.
   ● Body-> form-data
      file : /D:/Downloads/Ennea solutions/Book1.csv    

  # Endpoint
    GET /suplier_products/{supplier}
   ● Description:
        From the database all the product details of a supplier are fetched and filtered to get the records with stock value greater than zero. 


  # Endpoint
    GET suplier_products/{supplier}?product_name={product_name}&page={page_number}&limit={limit_value}
   ● Description:
         Records of a supplier are fetched and then filtered with product_name.
         
         
  # Endpoint
    GET suplier_products/{supplier}?expired={boolean_value}&page={page_number}&limit={limit_value}
   ● Description:  
          1) Read a supplier records and filter to get expired or non-expired records.
          2) The expire date(exp) in the records is compared with the current date to check whether the record is expired or not.
          3) parameter expired=true to get the expired records.
          4) Parameter expired=false to get the non-expired records.
