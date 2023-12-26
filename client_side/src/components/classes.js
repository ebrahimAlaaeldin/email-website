
export  class User{
    constructor(Uid,username){
        this.userId=Uid
        this.username=username
    }
}

export class PostEmailDto{
    constructor(Mid,From,To,Subject,Body,Date,Priority,Attachment){
        this.emailId=Mid
        this.sender=From
        this.receivers=To
        this.subject=Subject
        this.body=Body
        this.timestamp=Date
        this.attachments=Attachment
        this.priority=Priority
    }
}

export class Contact{
    constructor(Cid,name,email,UserId){
        this.contactId=Cid
        this.name=name
        this.contactEmails=email
        this.userId=UserId
    }
}

export class Folder{
    constructor(name,name,isRemovable,isRenamable){
        this.folderId=FID
        this.folderName=name
        this.isRemovable=isRemovable
        this.isRenameable=isRenamable
    }
}

export class Transfer{
    constructor(FID1,FID2,MIDs){
        this.folderIdSource=FID1
        this.folderIdDestination=FID2
        this.emailIds=MIDs
    }
}

export class Attachment{
    constructor(AID,path,filename){
        this.attachmentId=AID
        this.path=path
        this.filename=filename
        
    }

}
export class get_email{
    constructor(UID,FID,SearchBy,SearchFor,SortBy,PageNumber,NumberOfElements)
    {
        this.userId=UID
        this.folderId=FID
        this.searchType=SearchBy
        this.searchFor=SearchFor
        this.sortBy=SortBy
        this.pageNumber=PageNumber
        this.numberOfElementsInPage=NumberOfElements
    }
}

export class get_mails_and_maxP{
    constructor(mails,maxP){
        this.emails=mails
        this.numberOfPages=maxP
    }
}