## Building The Application
`mvn clean package`

## Testing The Application
`mvn test`

## Running The Application
The application will run on port 9001, as defined in the application.properties

`mvn spring-boot:run`

## Endpoints

Inside artifacts folder, there is a file with all API calls, that could be imported in Postman

##### Products:

`GET: /product`

`GET: /product/images`

`GET: /product/product`

`GET: /product/product/images`

`GET: /product/{PRODUCT_ID}`

`GET: /product/{PRODUCT_ID}/images`

`GET: /product/{PRODUCT_ID}/product`

`GET: /product/{PRODUCT_ID}/product/images`

`GET: /product/{PRODUCT_ID}/product/child`

`GET: /product/{PRODUCT_ID}/images/child`

`POST: /product
 BODY:
    {
        "name":string,
        "description":string,
        "product": {
            "name":string,
            "description":string,	
            "product": {},
            "images":[
                {
                    "type":string
                },
                {
                    "type":string
                }
            ]
        },
        "images":[
            {
                "type":string
            },
            {
                "type":string
            }
        ]
    }
`

`PUT: /product
 BODY:
    {
        "id":long,
        "name":string,
        "description":string,
        "product": {
            "id":long,
            "name":string,
            "description":string,	
            "product": {},
            "images":[
                {
                    "id":long,
                    "type":string
                },
                {
                    "id":long,
                    "type":string
                }
            ]
        },
        "images":[
            {
                "id":long,
                "type":string
            },
            {
                "id":long,
                "type":string
            }
        ]
    }
`

`DELETE: /product/{PRODUCT_ID}`

##### Images:

`GET: /image`

`GET: /image/{IMAGE_ID}`

`POST: /image
 BODY:
    {
        "type":string
    }
`

`PUT: /image
 BODY:
    {
        "id":long
        "type":string
    }
`

`DELETE: /image/{IMAGE_ID}`
