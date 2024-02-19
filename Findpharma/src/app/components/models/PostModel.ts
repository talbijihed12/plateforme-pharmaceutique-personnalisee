import { CommentPayload } from "./Comment-payload";
import { CommentModel } from "./CommentModel";

export class PostModel {
    id!: number;
    postName!: string;
    hashtag!: string;
    description!: string;
    username!: string;
    topicName!: string;
    commentCount!: number;
    duration!: string;
  
    subplateformeName: any;
    createdBy!: string;
    createdDate!: Date;
    lastModifiedBy!: string;
    lastModifiedDate!: Date;
    comments!: CommentModel;

    
  }