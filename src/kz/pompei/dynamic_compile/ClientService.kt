package kz.pompei.dynamic_compile

interface ClientService {

  fun loadClient(id: String): Client

  fun saveClient(client: Client)

}
