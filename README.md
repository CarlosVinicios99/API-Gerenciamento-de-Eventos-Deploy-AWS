# API Gerenciamento de Eventos e Deploy com AWS
<p>Neste projeto desenvolvo uma API para um domínio de gerenciamento de eventos, aonde é possível cadastrar, listar e remover eventos, como consultar todas as informações de eventos já realizados e eventos futuros com várias outras filtragens.</p>

## Deploy AWS
<p>Seguindo o objetivo do projeto que era utilizar serviços da AWS para realizar deploy, foi configurada uma VPC com subredes, uma privada para o Banco Postgres e outra pública para o EC2 (máquina que irá executar a API), um gateway foi configurado para permitir conexões externas com a VPC, por fim o S3 foi utilizado para salvar arquivos multimídia em geral.</p>

## Modelagem AWS
<img src="https://github.com/CarlosVinicios99/API-Gerenciamento-de-Eventos-Deploy-AWS/blob/main/modelagem-deploy.jpeg?raw=true">


