package com.unicom.excelforunicom.utils;

import java.util.List;

/**
 * 树形结构生成接口
 * @param <T>
 */
public interface TreeGenerater <T>{
   /**
    * 根据id获得节点对象
    * @param nodeId
    * @return
    */
   T getNodeById(int nodeId);

   /**
    * 根据id获取它的子节点的集合
    * @param nodeId
    * @return
    */
   List<T> getChildrenNodeById(int nodeId);

   /**
    * 递归生成树
    * @param rootId
    * @return
    */
   T generateTreeNode(int rootId);
}
