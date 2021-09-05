package com.flowkind.kubernetescloudnativeapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class KubernetesCloudNativeAppApplicationTests {

    String name = "HELLO";

    @Test
    void contextLoads() {
        assertThat(name).isEqualTo("HELLO");
    }

}
