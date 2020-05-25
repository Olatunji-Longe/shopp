package com.shopp.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTests extends ControllerIntegrationTest {

    @Test
    @DisplayName("Test bookController returns all valid books")
    public void testGetAllBooksEndpoint() throws Exception {

        MvcResult result = mockMvc.perform(
                get("/books", "")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andReturn();

        String json = result.getResponse().getContentAsString();
        Assertions.assertTrue(StringUtils.isNotBlank(json));

        JsonNode data = mapper.readValue(json, JsonNode.class);
        Assertions.assertNotNull(data);

        JsonNode booksNode = data.get("books");
        Assertions.assertTrue(booksNode.isArray());
        Assertions.assertFalse(booksNode.isEmpty());

        booksNode.elements().forEachRemaining(bookInstance -> Assertions.assertTrue(
    bookInstance.hasNonNull("id") &&
            bookInstance.hasNonNull("title") &&
            bookInstance.hasNonNull("author") &&
            bookInstance.hasNonNull("language") &&
            bookInstance.hasNonNull("year") &&
            bookInstance.hasNonNull("price")
        ));
    }

    @Test
    @DisplayName("Test bookController returns a valid book by bookId")
    public void testGetBookByIdEndpoint() throws Exception {

        MvcResult result = mockMvc.perform(
                get("/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andReturn();

        String json = result.getResponse().getContentAsString();
        Assertions.assertTrue(StringUtils.isNotBlank(json));

        JsonNode data = mapper.readValue(json, JsonNode.class);
        Assertions.assertNotNull(data);

        JsonNode bookNode = data.get("book");
        Assertions.assertTrue(bookNode.isContainerNode());

        Assertions.assertTrue(
                bookNode.hasNonNull("id") &&
                        bookNode.get("id").longValue() == 1L &&
                        bookNode.hasNonNull("title") &&
                        bookNode.hasNonNull("author") &&
                        bookNode.hasNonNull("language") &&
                        bookNode.hasNonNull("year") &&
                        bookNode.hasNonNull("price"));
    }

}
