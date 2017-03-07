You first need to compile both the applications using maven.

Below are the respective services which can run independently (Run as a normal main program):
productCatalog - ProductCatalogService.java
productPrice - ProductPriceService.java


Technology used - Vert.x for creating service because:
1. Polyglot feature.
2. Event loop helps in efficiently using CPU cycles.
3. Event bus can be used for clustered environments.
and many more...


Further combination of Consul + Docker can be used for service management


content-type : application/json

Catalog services
########################

GET
http://localhost:8080/products/:productID

PUT
http://localhost:8080/products
{"productId":"123",
"productName":"firstProduct",
"productPrice":100
}


GET
http://localhost:8080/products


DELETE
http://localhost:8080/products/:productID


Price Service
########################
http://localhost:8080/products/price/:productID