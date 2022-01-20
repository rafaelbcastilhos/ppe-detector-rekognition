# ppe-detector-rekognition

Aplicação utilizando AWS Lambda que detecta equipamento de proteção individual (EPI) por intermédio do AWS Rekognition em imagens localizadas em um bucket S3, e posteriormente salva os registros no AWS DynamoDB, além disso, a função Lambda cria uma lista de todas as imagens e envia a lista por e-mail utilizando AWS SES.

## Exemplos:

### ppe1.jpg
![ppe1](https://raw.githubusercontent.com/rafaelbcastilhos/ppe-detector-rekognition/main/src/main/resources/ppe1.jpg)

### ppe2.jpg
![ppe2](https://raw.githubusercontent.com/rafaelbcastilhos/ppe-detector-rekognition/main/src/main/resources/ppe2.jpg)

### ppe3.jpg
![ppe3](https://raw.githubusercontent.com/rafaelbcastilhos/ppe-detector-rekognition/main/src/main/resources/ppe3.jpg)

## Resultados
![Resultados](https://raw.githubusercontent.com/rafaelbcastilhos/ppe-detector-rekognition/main/src/main/resources/resultados.png)
