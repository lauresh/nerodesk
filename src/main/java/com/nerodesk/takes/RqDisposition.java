/**
 * Copyright (c) 2015, nerodesk.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the nerodesk.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.nerodesk.takes;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.takes.Request;
import org.takes.rq.RqHeaders;
import org.takes.rq.RqWrap;

/**
 * Extracts properties from Content-Disposition header.
 *
 * @author Carlos Alexandro Becker (caarlos0@gmail.com)
 * @version $Id$
 * @since 0.3.2
 */
public final class RqDisposition extends RqWrap {
    /**
     * Regex to get the filename from header.
     */
    private static final Pattern HEADER_FNAME = Pattern
        .compile(".*filename=\"(.*)\";?.*");

    /**
     * The contents of Content-Disposition header.
     */
    private final transient String content;

    /**
     * Ctor.
     * @param req Request.
     * @throws IOException In case of error.
     */
    public RqDisposition(final Request req) throws IOException {
        super(req);
        this.content = new RqHeaders(req)
            .header("Content-Disposition")
            .iterator()
            .next();
    }

    /**
     * Extracts the filename from Content-Disposition.
     * @return The filename.
     * @throws IOException If the filename header is not present.
     */
    public String filename() throws IOException {
        final Matcher matcher = HEADER_FNAME.matcher(this.content);
        if (!matcher.matches()) {
            throw new IOException(
                "filename isn't present in header"
            );
        }
        return matcher.group(matcher.groupCount());
    }
}
