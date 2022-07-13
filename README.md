# POO_Project
Object oriented programming project for class

Original Description:

Pretende-se o desenho e implementação de um sistema para gestão logística de uma empresa de transportes.
A empresa é contratada por clientes para efetuar o transporte de vários tipos de carga. Cada item
de carga tem uma descrição, nível de segurança, e quantidade de unidades.
A empresa efetua entregas de carga num destino, e permite depósitos de carga, provenientes de
uma determinada origem, no armazém central.
Cada depósito corresponde a colecionar carga num ponto de origem e carregar para um veículo,
transportar para o armazém central da empresa de transportes, e descarregar todo veículo para o
armazém central.
Cada entrega corresponde a carregar a carga descrita pelo cliente num veículo, e enviar para um
ponto de destino indicado pelo cliente.
A qualquer momento, um cliente tem um determinado inventário (eventualmente vazio) no armazém
central da empresa de transportes. Ao registar uma entrega deve indicar que itens, e em que quantidade,
constituem a entrega. A carga da entrega tem que estar disponível no armazém central.
Cada cliente terá atribuído um gestor responsável na empresa de transportes. Será também necessário atribuir condutores aos veículos utilizados (recolha e envio de carga em depósitos e entregas).
Quando um veículo é descarregado ou carregado é necessário atribuir a tarefa a um conjunto de funcionários carregadores.
O nível de segurança de um item de carga varia entre: normal, sensível, perigoso. Um item
sensível pode ser conduzido por qualquer condutor, mas tem que ser descarregado e carregado por um
funcionário carregador autorizado a lidar com estes itens. Um item perigoso deve ser conduzido por
um funcionário condutor autorizado a transportar estes itens, e pode ser carregado ou descarregado
por qualquer carregador. A tabela 1 apresenta as várias permissões, e o critério de ordenação aplicável.
Ordem Sigla Nome Descrição
1 N Normal Autorização padrão.
2 S Seguro Autorizado a interagir com itens com nível elevado de segurança.
3 P Perigoso Autorizado a interagir com itens perigosos.
Tabela 1: Permissões de funcionários.
A tabela 2 descreve todas as categorias profissionais disponíveis e as permissões de cada.
Categoria Permissões Descrição
Condutor N, P Conduz veículos.
Carregador N, S Efetua carga e descarga de veículos.
Gestor N Gere a relação com um cliente.
Tabela 2: Categorias de funcionários
