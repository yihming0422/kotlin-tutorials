package com.bennyhuo.kotlin.generics

object Main {
    type OnConfirm = () => Unit
    type OnCancel = () => Unit

    final val EmptyFunction = () => {}

    class Notification(val title: String, val content: String)

    class ConfirmNotification(
                                 title: String,
                                 content: String,
                                 val onConfirm: OnConfirm,
                                 val onCancel: OnCancel
                             ) extends Notification(title, content)

    class NotificationBuilder {
        self =>
        protected var title: String = ""
        protected var content: String = ""

        def title(title: String): self.type = {
            this.title = title
            return this
        }

        def content(content: String): self.type = {
            this.content = content
            return this
        }

        def build() = new Notification(this.title, this.content)
    }

    class ConfirmNotificationBuilder extends NotificationBuilder() {
        private var onConfirm: OnConfirm = EmptyFunction
        private var onCancel: OnCancel = EmptyFunction

        def onConfirm(onConfirm: => Unit): ConfirmNotificationBuilder = {
            this.onConfirm = () => {
                onConfirm
            }
            return this
        }

        def onCancel(onCancel: => Unit): ConfirmNotificationBuilder = {
            this.onCancel = () => {
                onCancel
            }
            return this
        }

        override def build() = new ConfirmNotification(title, content, onConfirm, onCancel)
    }

    def main(args: Array[String]): Unit = {
        new ConfirmNotificationBuilder()
            .title("Hello")
            .onCancel {
                println("onCancel")
            }
            .content("World")
            .onConfirm {
                println("onConfirm")
            }
            .build()
            .onConfirm()
    }

}

