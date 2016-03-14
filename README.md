# KasunMadusanka-jrsql
##### To use [rsql-mongodb](https://github.com/RutledgePaulV/rsql-mongodb) with sample Json models for type conversion.

[rsql-mongodb](https://github.com/RutledgePaulV/rsql-mongodb) uses Model.class for determine Entity Field Type of the rsql query fields.
It requires POJO or POGO(if you are using Groovy) classes to convert query value type. This may be a difficult situation, since we have to
make those set of classes to execute.
[Here](https://github.com/KasunMadusanka/KasunMadusanka-jrsql) I used a JSON sample model for the conversion purpose. (*Note that the JSON
is a sample model, not the schema)
