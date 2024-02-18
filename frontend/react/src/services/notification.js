import { createStandaloneToast } from '@chakra-ui/react'

const { toast } = createStandaloneToast()

const notification = (title, description, status) => {
    toast({
        title,
        description,
        status,
        isClosable: true,
        duration: 4000
    })
}

export const successNotification = () => {
    notification(
        title,
        description,
        "success"
    )
}

export const errorNotification = () => {
    notification(
        title,
        description,
        "error"
    )
}