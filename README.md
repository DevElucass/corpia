<img width="300" height="300" alt="badge" src="https://github.com/user-attachments/assets/515b47de-4393-40d4-beae-c30f144ddc89" />
CorpIA — Agente Inteligente RAG

Projeto desenvolvido para o Challenge Alura Agente, com o objetivo de demonstrar a construção de um agente de inteligência artificial capaz de responder perguntas com base no conteúdo de um documento PDF.



O CorpIA utiliza uma arquitetura RAG (Retrieval-Augmented Generation): o conteúdo do documento é extraído, dividido em chunks, transformado em representações vetoriais, armazenado no PostgreSQL com PGVector e recuperado semanticamente antes da geração da resposta pelo modelo de linguagem.



1\. Objetivo do projeto

Construir um agente inteligente que:



leia e processe um documento PDF;

transforme o conteúdo em conhecimento consultável;

realize busca semântica utilizando armazenamento vetorial;

utilize o contexto recuperado para responder perguntas;

evite inventar informações que não estejam presentes na base de conhecimento.

O documento utilizado como fonte principal do conhecimento é o currículo profissional Curr\_Prof\_Jun.pdf.



2\. Requisitos do Challenge atendidos

Repositório no GitHub

O projeto foi estruturado para disponibilização em um repositório público, mantendo histórico de commits e organização de código.



Documentação

Este README apresenta:



descrição do projeto;

arquitetura da solução;

tecnologias e ferramentas;

estrutura do projeto;

configuração;

execução local;

endpoints;

exemplos de perguntas e respostas;

informações de deploy e evidência.

Agente inteligente funcional

O agente recebe perguntas através da API e utiliza informações recuperadas do documento PDF por meio da arquitetura RAG.



Deploy na OCI

A seção de deploy está preparada para registrar a implantação final na Oracle Cloud Infrastructure (OCI), incluindo URL pública e evidência visual do funcionamento.



3\. Arquitetura da solução

Visão geral

&#x20;                        +----------------------+

&#x20;                        |    Curr\_Prof\_Jun.pdf |

&#x20;                        +----------+-----------+

&#x20;                                   |

&#x20;                                   v

&#x20;                        +----------------------+

&#x20;                        |      PdfExtractor     |

&#x20;                        |      Apache PDFBox    |

&#x20;                        +----------+-----------+

&#x20;                                   |

&#x20;                                   v

&#x20;                        +----------------------+

&#x20;                        |     Texto extraído    |

&#x20;                        +----------+-----------+

&#x20;                                   |

&#x20;                                   v

&#x20;                        +----------------------+

&#x20;                        |   TokenTextSplitter   |

&#x20;                        |      Chunking/RAG      |

&#x20;                        +----------+-----------+

&#x20;                                   |

&#x20;                                   v

&#x20;                        +----------------------+

&#x20;                        |      Embeddings       |

&#x20;                        +----------+-----------+

&#x20;                                   |

&#x20;                                   v

&#x20;                        +----------------------+

&#x20;                        | PostgreSQL + PGVector  |

&#x20;                        |      vector\_store      |

&#x20;                        +----------+-----------+

&#x20;                                   |

&#x20;                                   |

&#x20;                   Pergunta       |

&#x20;                      |            |

&#x20;                      v            v

&#x20;                +-----------------------+

&#x20;                | Busca semântica       |

&#x20;                | similaritySearch()    |

&#x20;                +-----------+-----------+

&#x20;                            |

&#x20;                            v

&#x20;                +-----------------------+

&#x20;                | QuestionAnswerAdvisor |

&#x20;                +-----------+-----------+

&#x20;                            |

&#x20;                            v

&#x20;                +-----------------------+

&#x20;                |    OpenAI GPT-4o-mini  |

&#x20;                +-----------+-----------+

&#x20;                            |

&#x20;                            v

&#x20;                +-----------------------+

&#x20;                |    Resposta do agente  |

&#x20;                +-----------------------+



Fluxo resumido

PDF

&#x20;↓

PDFBox

&#x20;↓

Texto

&#x20;↓

TokenTextSplitter

&#x20;↓

Chunks

&#x20;↓

Embeddings

&#x20;↓

PGVector

&#x20;↓

Busca semântica

&#x20;↓

Contexto recuperado

&#x20;↓

QuestionAnswerAdvisor

&#x20;↓

GPT-4o-mini

&#x20;↓

Resposta



4\. Tecnologias utilizadas

Backend

Java 26

Spring Boot 4.1.0

Spring Web MVC

Spring Data JPA

Maven

Inteligência Artificial e RAG

Spring AI 2.0.0

OpenAI GPT-4o-mini

ChatClient

QuestionAnswerAdvisor

VectorStore

TokenTextSplitter

Embeddings

RAG — Retrieval-Augmented Generation

Banco de dados

PostgreSQL 18.4

PGVector 0.8.6

Banco: corpia\_rag

Tabela vetorial: vector\_store

Porta local: 5432

Processamento de documentos

Apache PDFBox 3.0.5 — extração do conteúdo do PDF

Apache POI 5.4.1 — suporte a documentos Office

Frontend

HTML5

CSS3

JavaScript

Ferramentas de desenvolvimento

Visual Studio Code

Visual Studio Community 2026

PowerShell 7

Git

GitHub

Maven

Postman

Docker / Docker Compose

Infraestrutura

Oracle Cloud Infrastructure (OCI) para o deploy da aplicação

PostgreSQL

PGVector

Controle de versão

Git

GitHub

Histórico de commits para registrar a evolução do projeto

5\. Estrutura do projeto

CorpIA/

│

├── backend/

│   └── corpia-api/

│       ├── .mvn/

│       ├── docker/

│       │   └── docker-compose.yml

│       ├── documentos/

│       ├── src/

│       │   ├── main/

│       │   │   ├── java/

│       │   │   │   └── br/com/corpia/

│       │   │   │       ├── config/

│       │   │   │       ├── controller/

│       │   │   │       ├── dto/

│       │   │   │       ├── model/

│       │   │   │       ├── repository/

│       │   │   │       ├── service/

│       │   │   │       └── util/

│       │   │   └── resources/

│       │   │       └── application.properties

│       │   └── test/

│       ├── pom.xml

│       ├── mvnw

│       └── mvnw.cmd

│

├── documentos/

├── frontend/

│   └── index.html

├── README.md

└── .gitignore



6\. Principais componentes do backend

PdfExtractor

Classe responsável por carregar o PDF e extrair seu conteúdo textual utilizando o Apache PDFBox.



RagIndexService

Responsável pelo fluxo de indexação e recuperação:



leitura do PDF;

criação dos chunks;

inclusão de metadados;

armazenamento no VectorStore;

busca semântica.

ChatbotService

Responsável pela interação com o ChatClient e pelo uso do contexto recuperado pelo RAG.



O serviço também define instruções para que o agente responda utilizando exclusivamente as informações recuperadas da base de conhecimento e informe quando determinada informação não estiver disponível.



ChatbotController

Disponibiliza o endpoint HTTP para envio das perguntas ao agente.



RagIndexController

Disponibiliza endpoints para:



indexação do conhecimento;

teste da busca semântica.

7\. Documento de conhecimento

Documento principal utilizado como fonte do agente:



Curr\_Prof\_Jun.pdf



O arquivo contém informações profissionais, formação acadêmica, competências, cursos, experiências e resultados profissionais do candidato.



8\. Indexação do documento

O endpoint:



POST /rag/indexar



realiza a indexação do PDF.



Exemplo:



curl.exe -i -X POST "http://127.0.0.1:8082/rag/indexar"



No ambiente validado durante o desenvolvimento, o PDF gerou:



Conhecimento indexado com sucesso. Chunks gerados: 3



9\. Teste da busca semântica

Endpoint:



GET /rag/testar?pergunta=...



Exemplo:



curl.exe -s "http://127.0.0.1:8082/rag/testar?pergunta=Qual%20foi%20a%20atua%C3%A7%C3%A3o%20do%20candidato%20na%20STYLUSFINO?"



A busca recupera chunks relevantes do Curr\_Prof\_Jun.pdf e apresenta seus metadados e conteúdo.



10\. Chatbot

Endpoint:



POST /chatbot/perguntar



Exemplo:



curl.exe -s -X POST "http://127.0.0.1:8082/chatbot/perguntar" `

&#x20; -H "Content-Type: application/json" `

&#x20; -d '{"pergunta":"Qual foi a atuação do candidato na STYLUSFINO?"}'



11\. Exemplos de perguntas que o agente consegue responder

Experiência profissional

Pergunta:



Qual foi a atuação do candidato na STYLUSFINO?



Resposta validada:



O candidato atuou como Coordenador de Logística na STYLUSFINO de 2015 a 2024. Suas principais responsabilidades incluíam a coordenação das operações logísticas e distribuição, gestão de equipes operacionais e motoristas, controle de carregamento e descarregamento, planejamento operacional diário, monitoramento de entregas e desempenho operacional, gestão de indicadores logísticos, controle de produtividade e nível de serviço, acompanhamento de SLA e OTIF, além do controle operacional de estoque e fluxo logístico.



Formação acadêmica

Pergunta:



Quais são as formações acadêmicas do candidato?



O agente recupera as informações de formação disponíveis no currículo e apresenta os dados encontrados na fonte.



Competências

Pergunta:



Quais são as competências profissionais do candidato?



O agente recupera informações relacionadas a competências como:



Excel

SAP

WMS

Lean

Supply Chain

Logística

Gestão de Estoque

Gestão de Transportes

Gestão de Centros de Distribuição

Gestão de Processos

Liderança de Equipes

Eficiência Operacional

Informação ausente

Pergunta:



Qual era o salário do candidato na STYLUSFINO?



Essa informação não está presente na fonte de conhecimento utilizada nos testes. O agente foi configurado para informar que não encontrou a informação, em vez de inventar um valor.



12\. Configuração de ambiente

As credenciais devem ser fornecidas por variáveis de ambiente e não devem ser publicadas no código-fonte.



Exemplo:



POSTGRES\_USER=postgres

POSTGRES\_PASSWORD=sua\_senha

OPENAI\_API\_KEY=sua\_chave\_openai



A aplicação utiliza a variável OPENAI\_API\_KEY para acessar o modelo de IA e a variável POSTGRES\_PASSWORD para a autenticação do banco.



13\. Execução local

Entre na pasta do backend:



cd "C:\\Users\\dluca\\OneDrive\\Área de Trabalho\\CorpIA\\backend\\corpia-api"



Suba a aplicação:



mvn spring-boot:run



A aplicação utiliza a porta:



8082



Teste a porta:



Test-NetConnection 127.0.0.1 -Port 8082



Resultado esperado:



TcpTestSucceeded : True



14\. Banco de dados

Configuração utilizada no ambiente local:



PostgreSQL: 18.4

Host: 127.0.0.1

Porta: 5432

Banco: corpia\_rag



Extensão vetorial:



PGVector: 0.8.6



Extensão validada:



vector | 0.8.6



O Spring AI utiliza a tabela:



vector\_store



15\. Testes realizados

Durante a implementação foram realizados testes de:



conectividade com PostgreSQL;

autenticação do usuário postgres;

conexão com o banco corpia\_rag;

disponibilidade da extensão PGVector;

compilação do projeto Maven;

inicialização do Spring Boot;

indexação do PDF;

geração de chunks;

busca semântica;

geração de respostas do chatbot;

comportamento para informações ausentes.

Exemplo de resultado validado:



POST /rag/indexar

HTTP 200

Chunks gerados: 3



E:



POST /chatbot/perguntar

HTTP 200

Resposta baseada no Curr\_Prof\_Jun.pdf



16\. Segurança

O projeto não deve publicar:



chaves de API;

senhas de banco;

arquivos .env reais;

arquivos temporários;

backups de código;

artefatos de compilação.

Esses arquivos são tratados pelo .gitignore e por variáveis de ambiente.



17\. Histórico de desenvolvimento

O desenvolvimento foi registrado em commits Git, permitindo acompanhar a evolução do sistema.



Principais marcos do histórico local:



feat: estabiliza RAG com PDF e PGVector

chore: limpa repositorio e atualiza frontend

chore: ajusta gitignore

security: remove secrets from configuration

chore: ajusta gitignore da raiz



18\. Frontend

O projeto possui uma interface web em frontend/index.html, criada para proporcionar uma experiência de conversa com o agente.



A interface contempla:



cabeçalho da aplicação;

identificação do CorpIA;

área de mensagens;

mensagens do usuário e do agente;

indicador de carregamento;

campo para envio de perguntas;

botão de envio;

controle de limpeza da conversa.

19\. Frontend publicado na Vercel

O frontend do CorpIA foi publicado na Vercel.



Aplicação online

🚀 Acessar o CorpIA



A Vercel hospeda o frontend da aplicação localizado em:



frontend/index.html



20\. Deploy na Oracle Cloud Infrastructure (OCI)

A etapa final do projeto contempla o deploy do backend da aplicação na Oracle Cloud Infrastructure (OCI).



A entrega deve registrar:



instância utilizada;

endereço IP ou domínio público;

porta pública da aplicação;

configuração das variáveis de ambiente;

execução do backend na nuvem;

funcionamento do agente através da interface web.

URL pública

Ainda não disponibilizada.



Evidência do deploy

Adicionar nesta seção uma captura de tela mostrando o backend e/ou a aplicação executando na OCI e respondendo a uma pergunta do agente.



21\. Entrega do Challenge

Repositório no GitHub

📦 Repositório GitHub — CorpIA

Código-fonte versionado com Git.

Histórico de commits disponível no repositório.

README

Este documento apresenta:



descrição geral;

arquitetura;

tecnologias e ferramentas;

estrutura do projeto;

instruções de execução;

endpoints;

exemplos de perguntas;

exemplos de respostas.

Agente inteligente funcional

O agente responde perguntas com base no conteúdo do arquivo:



Curr\_Prof\_Jun.pdf



Evidência do deploy na OCI

Após o deploy, esta seção deve conter:



URL pública;

print da aplicação funcionando;

demonstração de uma consulta respondida pelo agente.

23\. Badge de conclusão do Challenge Alura

Projeto desenvolvido e concluído como parte do Challenge Alura RAG — Agente de IA.



Badge Alura - Challenge RAG Agente IA



24\. Autor

Enivaldo Lucas de Souza



Projeto desenvolvido para o Challenge Alura Agente.

