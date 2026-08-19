package br.com.corpia.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class ChatbotService {

    private final ChatClient chatClient;

    public ChatbotService(
            ChatClient.Builder chatClientBuilder,
            VectorStore vectorStore) {

        this.chatClient = chatClientBuilder

                .defaultAdvisors(
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(
                                        SearchRequest.builder()
                                                .topK(8)
                                                .similarityThreshold(0.20)
                                                .build())
                                .build())

                .defaultSystem("""
                        Você é o assistente inteligente da CorpIA.

                        Sua função é responder perguntas utilizando EXCLUSIVAMENTE
                        as informações presentes no contexto recuperado da base
                        de conhecimento.

                        REGRAS OBRIGATÓRIAS:

                        1. Nunca invente informações.

                        2. Nunca diga que uma informação não existe se ela estiver
                           presente no contexto recuperado.

                        3. Quando a pergunta solicitar resultados profissionais,
                           procure cuidadosamente por seções como:
                           - RESULTADOS ALCANÇADOS
                           - resultados
                           - indicadores
                           - produtividade
                           - redução
                           - aumento
                           - melhoria
                           - percentuais
                           - OTIF
                           - SLA
                           - avarias
                           - perdas
                           - custos
                           - estoque.

                        4. Quando a pergunta pedir resultados por empresa,
                           organize obrigatoriamente a resposta por empresa.

                        5. Preserve os percentuais e indicadores exatamente como
                           aparecem no contexto.

                        6. Não transforme uma informação qualitativa em percentual
                           se o percentual não estiver explicitamente informado.

                        7. Não atribua um resultado de uma empresa a outra.

                        8. Se houver informações de mais de uma empresa no contexto,
                           utilize todas as informações relevantes disponíveis.

                        9. Se uma empresa aparecer no contexto e houver uma seção
                           "RESULTADOS ALCANÇADOS", considere essas informações
                           na resposta.

                        10. Se a informação realmente não estiver no contexto,
                            diga claramente que ela não foi encontrada.

                        11. Responda em português do Brasil.

                        12. Seja objetivo, profissional e fiel ao currículo.

                        Ao responder perguntas sobre resultados profissionais,
                        dê preferência aos dados concretos e mensuráveis.
                        """)
                .build();
    }

    public String responder(String pergunta) {

        if (pergunta == null || pergunta.isBlank()) {
            return "Por favor, informe uma pergunta.";
        }

        return chatClient
                .prompt()
                .user(pergunta)
                .call()
                .content();
    }
}
