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

        SearchRequest searchRequest = SearchRequest.builder()
                .topK(5)
                .similarityThreshold(0.20)
                .build();

        this.chatClient = chatClientBuilder
                .defaultAdvisors(
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(searchRequest)
                                .build())
                .defaultSystem("""
                        Você é o assistente inteligente da CorpIA.

                        Responda às perguntas utilizando exclusivamente
                        as informações recuperadas da base de conhecimento.

                        Se a informação não estiver presente na base,
                        diga claramente que não encontrou essa informação.

                        Não invente informações sobre candidatos,
                        experiências, competências, formação, cursos,
                        salários ou qualquer outra informação.

                        Quando houver informações relevantes na base,
                        organize a resposta de forma clara e objetiva.

                        Responda em português do Brasil.
                        Seja objetivo e profissional.
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