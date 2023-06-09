//导入request
import request from './request'
import { useAppStore } from '../store/app'
const store = useAppStore()

//存放api

//添加文章
// topic:标题
// content:正文
// image:封面
// userId:用户ID
export const addArticle = (data,type) => {
    return request({
        method: 'post',
        url: `/api/${type}/add`,
        data,
        headers: { 'Content-Type': 'multipart/form-data' },
    })
}

//获取所有文章
export const getArticles = type => {
    return request({
        method: 'get',
        url: `/api/${type}/get/user/${store.userId}`,
    })
}

//获取文章图片
export const getPic =( id,type) => {
    return request({
        method: 'get',
        url: `/api/${type}/photo/get/${id}`,
        responseType: 'blob'
    })
}

//更新文章
// id
// topic
// content
// image
export const updateArticle = (data,type) => {
    return request({
        method: 'post',
        url: `/api/${type}/update`,
        data,
        headers: { 'Content-Type': 'multipart/form-data' },
    })
}

//删除文章
export const deleteArticle = (id,type) => {
    return request({
        method: 'get',
        url: `/api/${type}/delete/${id}`,
    })
}