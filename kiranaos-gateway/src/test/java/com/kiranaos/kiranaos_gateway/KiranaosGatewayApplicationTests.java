package com.kiranaos.kiranaos_gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KiranaosGatewayApplicationTests {

	@LocalServerPort
	private int port;

	@Test
	void contextLoads() {
	}

	@Test
	void gatewayPortShouldBe8080(){
		assertEquals(8080, port);
	}
}