/**
 * Copyright (c) 2016 Bernard Leach
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
package com.github.leachbj.newrelic.akka.http.scaladsl

import akka.event.LoggingAdapter
import akka.http.scaladsl.Http.ClientLayer
import akka.http.scaladsl.model.headers.Host
import akka.http.scaladsl.settings.ClientConnectionSettings
import com.newrelic.api.agent.weaver.{Weave, Weaver}

@Weave(originalName = "akka.http.scaladsl.HttpExt")
abstract class HttpExt {
  @Weave
  @SuppressWarnings(Array("UnusedMethodParameter"))
  def clientLayer(hostHeader: Host): ClientLayer = {
    val client = Weaver.callOriginal[ClientLayer]()
    wrap(client)
  }

  @Weave
  @SuppressWarnings(Array("UnusedMethodParameter"))
  def clientLayer(hostHeader: Host, settings: ClientConnectionSettings, log: LoggingAdapter): ClientLayer = {
    val client = Weaver.callOriginal[ClientLayer]()
    wrap(client)
  }

  private def wrap(client: ClientLayer): ClientLayer = NewRelicClientLayer.metrics.atop(client)
}

