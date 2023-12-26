
export  class User{
    constructor(name){
        this.name=name
    }
}

export class Mail{
    constructor(From,To,Subject,Body,Date,Pirority,Attachment){
        this.From=From
        this.To=To
        this.Subject=Subject
        this.Body=Body
        this.Date=Date
        this.Attachment=Attachment
        this.Pirority=Pirority
    }
}

export class Contact{
    constructor(name,email,User){
        this.name=name
        this.email=email
        this.User=User
    }
}

export class Folder{
    constructor(name,User){
        this.name=name
        this.User=User
    }
}