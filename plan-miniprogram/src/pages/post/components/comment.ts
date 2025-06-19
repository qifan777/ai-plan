export type PostCommentResponse = {
  id: string;
  content: string;
  createdTime: string;
  parent?:
    | {
        id: string;
        creator: {
          id: string;
          nickname?: string | undefined;
          avatar?: string | undefined;
        };
      }
    | undefined;
  children?: PostCommentResponse[] | undefined;
  creator: {
    id: string;
    nickname?: string | undefined;
    avatar?: string | undefined;
  };
};

export const processPostComment = (data: PostCommentResponse[]) => {
  function fn(id?: string) {
    const arr: PostCommentResponse[] = [];
    for (let i = 0; i < data.length; i++) {
      if (data[i].parent?.id == id) {
        arr.push({ ...data[i], children: fn(data[i].id) });
      }
    }
    return arr;
  }
  return fn();
};
