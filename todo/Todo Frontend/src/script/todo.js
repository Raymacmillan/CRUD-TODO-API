class TodoList {
     static async getTodos() {
        try{
           const response = await fetch("todo/task",{});
           console.log(response);
           const todos = await response.json();
           console.log(todos);
           return todos;

        }catch(err){
           console.log(err);
           return [];
        }
     }

     static async getTodoByID(id) {

     }

     static async createTodo(todoItem) {

     }

     static async updateTodo(id,updatedData) {

     }

     static async deleteTodos() {

     }

     static async deleteTodoByID(id) {

     }
}

export default TodoList;