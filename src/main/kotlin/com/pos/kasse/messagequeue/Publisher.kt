package com.pos.kasse.messagequeue

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import java.io.IOException
import java.nio.charset.StandardCharsets

object Publisher {

    var connectionfactory: ConnectionFactory = ConnectionFactory()
    lateinit var connection: Connection
    lateinit var channel: Channel
    init {
        connectionfactory.host = "localhost"
        try {
            connection = connectionfactory
                    .newConnection("ampg://vare-api:varer@localhost:5672")
            channel = connection.createChannel()
            channel.queueDeclare("Salgsmelding", false, true, false, null)
        } catch (e: Exception) { print(e.stackTrace) }
    }

    fun publish(message: String) {
        try {
            this.channel.basicPublish(
                    "",
                    "Salgsmelding",
                    null,
                    message.toByteArray(StandardCharsets.UTF_8)
            )
            print("suksess")
        }
        catch (e: IOException) {
            print(e.stackTrace)
        }
    }
}