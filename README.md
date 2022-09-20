# Progress assessment

Progress soft assessment

A simple rest api to takes in FX deal details and saves to DB

# Requirements
To run this application, you'll need docker installed.
Additionally, you should have make installed also
clone the [repository](https://github.com/odamilola36/clustered-data-warehouse.git)
* cd into ```clustered-data-warehouse```
* run ```docker-compose up or make``` on the terminal (with docker installed)
* the application runs on port ```8090```


## API Reference

All URIs are relative to *http://localhost:8090*


### Data Api

| Method     | HTTP request                    | Description | 
|------------|---------------------------------|-------------|
| [**Post**] | **Post** /api/deals/create_deal | save deal   |    

```json
{
  "orderingCurrencyISO": "EUR",
  "toCurrencyISO": "NGN",
  "amountInOrderingCurrency": 10000.00,
  "dealUniqueId": "88-98skj-de3",
  "dealTimestamp": "2022-09-16T23:00:06Z"
}
```
```
Response
* Success: 201
* Client error 4xx
```


| Method    | HTTP request                 | Description | 
|-----------|------------------------------|-------------|
| [**Get**] | **Post** /api/deals/{dealId} | save deal   |

```
Response
* Success: 201
* Client error 4xx
```