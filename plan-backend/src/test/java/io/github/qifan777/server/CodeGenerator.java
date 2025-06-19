package io.github.qifan777.server;

import io.qifan.infrastructure.generator.processor.QiFanGenerator;

public class CodeGenerator {
    public static void main(String[] args) {
        QiFanGenerator qiFanGenerator = new QiFanGenerator();
        qiFanGenerator.process("io.github.qifan777.server", "template");
    }
}
