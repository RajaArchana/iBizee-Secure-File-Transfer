import { Users } from "./Users"

export class UserResponse {
    responseStatus: number = 0
    responseDescription: string = ""
    users: Users | undefined
}