/*
 * The MIT License
 *
 * Copyright 2019 Tribunal Superior do Trabalho.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package br.jus.tst.tio.classpath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Lê um recurso existente no classpath.
 * @since 0.1
 */
public final class Resource {
    /**
     * Caminho para o recurso.
     */
    private final String path;

    /**
     * Construtor principal.
     * @param path Caminho para o recurso.
     */
    public Resource(final String path) {
        this.path = path;
    }

    /**
     * Lê todo o conteúdo do recurso e o coloca em uma string.
     * @return Conteúdo do recurso.
     */
    public String read() {
        try {
            final BufferedReader reader = new BufferedReader(this.open());
            final StringBuilder builder = new StringBuilder();
            String linha = reader.readLine();
            while (linha != null) {
                builder.append(linha).append('\n');
                linha = reader.readLine();
            }
            return builder.toString();
        } catch (final IOException ex) {
            throw new ResourceException(
                String.format("Falha ao ler o recurso: %s", this.path),
                ex
            );
        }
    }

    /**
     * Cria um Reader para permitir que o cliente possa decidir como lerá o
     *  recurso.
     * @return Reader.
     */
    public Reader open() {
        return new InputStreamReader(
            Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(this.path)
        );
    }

    /**
     * Erros no processamento de recursos que estão no classpath.
     * @since 0.1
     */
    public static class ResourceException extends RuntimeException {

        /**
         * Construtor principal.
         * @param message Descreve o erro.
         * @param cause Causa do erro.
         */
        public ResourceException(final String message, final Throwable cause) {
            super(message, cause);
        }

    }
}
