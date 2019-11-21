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

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import org.apache.commons.io.input.BrokenInputStream;

/**
 * Simula falha na leitura de um recurso do classpath ao sobrescrever o método
 * getResourceAsStream de <code>java.net.URLClassLoader</code> para retornar uma
 * instcia de <code>org.apache.commons.io.input.BrokenInputStream</code>.
 * @since 0.1
 */
public final class BrokenUrlClassLoader extends URLClassLoader {

    /**
     * Chama o construtor da superclasse passando um array vazio de URLs, pois
     * não é necessário realmente carregar recursos no classpath.
     */
    public BrokenUrlClassLoader() {
        super(new URL[]{});
    }

    @Override
    public InputStream getResourceAsStream(final String name) {
        return new BrokenInputStream();
    }

}
