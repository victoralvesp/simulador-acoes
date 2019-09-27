# Sumario

Aplicação para simular flutuações de preço em ações para a API [Corretor Inteligente](https://github.com/victoralvesp/CorretorInteligente.git)

## Como utilizar

1. Clone o repositório
    ```git
    git clone https://github.com/victoralvesp/simulador-acoes.git
    ```
1. Navegue até o diretório do arquivo e monte o pacote:
    ```
    mvn package 
    ```
1. Execute a aplicação:
   ```java
    java -jar ./target/Simular-0.0.1-SNAPSHOT.jar
   ```

> A aplicação irá executar solicitações de inserção de
>  `Observação de ação` no servidor `http://localhost:8080`. 
> Caso prefira altere a chamada para a url desejada
```java
java -jar ./target/Simular-0.0.1-SNAPSHOT.jar http://seuservidor:suaporta
```

