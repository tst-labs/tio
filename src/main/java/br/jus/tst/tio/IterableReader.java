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

package br.jus.tst.tio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Transforma um <code>Reader</code> em um <code>Iterable</code>.
 * @since 0.1
 */
public final class IterableReader implements Iterable<String> {

    /**
     * BufferedReader.
     */
    private final BufferedReader reader;

    /**
     * Main constructor.
     * @param reader BufferedReader.
     */
    public IterableReader(final BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * Decorates a Reader with a BufferedReader.
     * @param reader Reader.
     */
    public IterableReader(final Reader reader) {
        this(new BufferedReader(reader));
    }

    @Override
    public Iterator<String> iterator() {
        return new BufferedReaderIterator(this.reader);
    }

    /**
     * Iterates over a BufferedReader.
     * @since 0.1
     */
    private static class BufferedReaderIterator implements Iterator<String> {

        /**
         * BufferedReader.
         */
        private final BufferedReader reader;

        /**
         * Next line.
         */
        private String linha;

        /**
         * Main constructor.
         * @param reader BufferedReader.
         */
        BufferedReaderIterator(final BufferedReader reader) {
            this.reader = reader;
        }

        @Override
        public boolean hasNext() {
            this.linha = this.read();
            return this.linha != null;
        }

        @Override
        public String next() {
            if (this.linha == null) {
                throw new NoSuchElementException("Fim do reader atingido");
            }
            return this.linha;
        }

        /**
         * Reads one line.
         * @return One line.
         */
        private String read() {
            try {
                return this.reader.readLine();
            } catch (final IOException ex) {
                throw new ReadException(ex);
            }
        }
    }

    /**
     * Iterators can't throw IOException.
     * @since 0.1
     */
    private static class ReadException extends RuntimeException {

        /**
         * Main constructor.
         * @param cause Root cause.
         */
        ReadException(final Throwable cause) {
            super(cause);
        }

    }
}
