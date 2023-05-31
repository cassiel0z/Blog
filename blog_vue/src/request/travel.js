//导入request
import request from './request'

//存放api

//添加文章
// topic:标题
// content:征文
// image:正文
// userId:用户ID
export const addArticle = params => {
    return request({
        method: 'post',
        url: `/api/travel/add`,
        params
    })
}

//获取文章
export const getArticle = id => {
    return request({
        method: 'get',
        url: `/api/travel/get/${id}`,
    })
}


//删除文章
export const deleteArticle = id => {
    return request({
        method: 'get',
        url: `/api/travel/delete/${id}`,
    })
}