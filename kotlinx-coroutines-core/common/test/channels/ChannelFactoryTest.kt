/*
 * Copyright 2016-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.coroutines.channels

import kotlinx.coroutines.*
import kotlin.test.*


class ChannelFactoryTest : TestBase() {
    @Test
    fun testRendezvousChannel() {
        assertTrue(Channel<Int>() is RendezvousChannel)
        assertTrue(Channel<Int>(0) is RendezvousChannel)
    }

    @Test
    fun testLinkedListChannel() {
        assertTrue(Channel<Int>(Channel.UNLIMITED) is LinkedListChannel)
        assertTrue(Channel<Int>(Channel.UNLIMITED, BufferOverflow.KEEP_LATEST) is LinkedListChannel)
        assertTrue(Channel<Int>(Channel.UNLIMITED, BufferOverflow.DROP_LATEST) is LinkedListChannel)
    }

    @Test
    fun testConflatedChannel() {
        assertTrue(Channel<Int>(Channel.CONFLATED) is ConflatedChannel)
        assertTrue(Channel<Int>(1, BufferOverflow.KEEP_LATEST) is ConflatedChannel)
    }

    @Test
    fun testArrayChannel() {
        assertTrue(Channel<Int>(1) is ArrayChannel)
        assertTrue(Channel<Int>(1, BufferOverflow.DROP_LATEST) is ArrayChannel)
        assertTrue(Channel<Int>(10) is ArrayChannel)
    }

    @Test
    fun testInvalidCapacityNotSupported() {
        assertFailsWith<IllegalArgumentException> { Channel<Int>(-3) }
    }
    
    @Test
    fun testUnsupportedBufferOverflow() {
        assertFailsWith<IllegalArgumentException> { Channel<Int>(Channel.RENDEZVOUS, BufferOverflow.KEEP_LATEST) }
        assertFailsWith<IllegalArgumentException> { Channel<Int>(Channel.RENDEZVOUS, BufferOverflow.DROP_LATEST) }
        assertFailsWith<IllegalArgumentException> { Channel<Int>(Channel.CONFLATED, BufferOverflow.KEEP_LATEST) }
        assertFailsWith<IllegalArgumentException> { Channel<Int>(Channel.CONFLATED, BufferOverflow.DROP_LATEST) }
    }
}
