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

import java.io.IOException;
import java.net.URLClassLoader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Testes para a classe Resource.
 *
 * @since 0.1
 */
public final class ResourceTest {

    /**
     * Resource pode lançar ResourceException caso haja alguma falha na leitura
     * de um recurso no classpath.
     *
     * @throws java.lang.Exception Em caso de falha.
     */
    @Test
    public void testRead() throws Exception {
        final ClassLoader ctx = Thread.currentThread().getContextClassLoader();
        try {
            final URLClassLoader loader = new BrokenUrlClassLoader();
            Thread.currentThread().setContextClassLoader(loader);
            final Resource instance = new Resource("recurso/problematico");
            final Throwable thrown = Assertions
                .catchThrowable(() -> instance.read());
            Assertions.assertThat(thrown)
                .hasCauseInstanceOf(IOException.class)
                .hasMessage("Falha ao ler o recurso: recurso/problematico");
        } finally {
            Thread.currentThread().setContextClassLoader(ctx);
        }
    }

}
