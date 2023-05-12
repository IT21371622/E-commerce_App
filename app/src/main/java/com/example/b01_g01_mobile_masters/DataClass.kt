class DataClass {
    // Stores the data
    var dataName: String? = null
    var dataEmail: String? = null
    var dataComment: String? = null
    var dataImage: String? = null

    constructor(dataName: String?, dataEmail: String?, dataComment: String?, dataImage: String?) {
        // Constructor with parameters to initialize the data fields
        this.dataName = dataName
        this.dataEmail = dataEmail
        this.dataComment = dataComment
        this.dataImage = dataImage
    }

    constructor() {
        // Empty constructor, useful when creating an instance without providing initial values
        // You can later set values to the fields individually
    }
}
