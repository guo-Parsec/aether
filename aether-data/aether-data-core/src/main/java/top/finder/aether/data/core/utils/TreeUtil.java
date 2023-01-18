package top.finder.aether.data.core.utils;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;
import top.finder.aether.data.core.entity.ITreeNode;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * <p>树结构工具类</p>
 *
 * @author guocq
 * @since 2023/1/18
 */
public class TreeUtil {
    /**
     * <p>构建root元素</p>
     *
     * @param nodes  节点列表
     * @param rootId 根节点id
     * @author guocq
     * @date 2023/01/18 16:40
     */
    public static <K extends Serializable, T extends ITreeNode<K>> void buildRoot(List<T> nodes, Long rootId) {
        if (rootId == null) {
            nodes.forEach(node -> node.setRoot(false));
            return;
        }
        nodes.forEach(node -> {
            if (rootId.equals(node.getId())) {
                node.setRoot(Boolean.TRUE);
                return;
            }
            node.setRoot(Boolean.FALSE);
        });
    }

    /**
     * <p>生成树状结构</p>
     *
     * @param nodes 节点列表
     * @return java.util.List<T>
     * @author guocq
     * @date 2023/01/18 16:40
     */
    public static <T extends ITreeNode<Long>> List<T> generateTree(List<T> nodes) {
        return generateTree(nodes, 1L);
    }

    /**
     * <p>生成树状结构</p>
     *
     * @param nodes  节点列表
     * @param rootId 根节点id
     * @return java.util.List<T>
     * @author guocq
     * @date 2023/01/18 16:40
     */
    public static <K extends Serializable, T extends ITreeNode<K>> List<T> generateTree(List<T> nodes, Long rootId) {
        List<T> roots = Lists.newArrayList();
        for (Iterator<T> ite = nodes.iterator(); ite.hasNext(); ) {
            T node = ite.next();
            // 第一次遍历时将所有节点设置为叶子节点
            node.setLeaf(Boolean.TRUE);
            if (node.isRoot() || ObjectUtil.equals(node.getId(), rootId)) {
                roots.add(node);
                // 从所有节点列表中删除当前节点，避免重复遍历
                ite.remove();
            }
        }
        roots.forEach(root -> {
            root.setRoot(Boolean.TRUE);
            setChildren(root, nodes);
        });
        return roots;
    }

    /**
     * <p>从节点列表中查找并设置parentNode的所有子节点</p>
     *
     * @param parentNode 父节点
     * @param nodes      节点列表
     * @author guocq
     * @date 2023/01/18 16:40
     */
    public static <K extends Serializable, T extends ITreeNode<K>> void setChildren(T parentNode, List<T> nodes) {
        List<T> children = Lists.newArrayList();
        K pid = parentNode.getId();
        for (Iterator<T> ite = nodes.iterator(); ite.hasNext(); ) {
            T node = ite.next();
            if (ObjectUtil.equals(node.getParentId(), pid)) {
                // 当前节点如果有子元素，则设置为非叶子节点
                parentNode.setLeaf(Boolean.FALSE);
                children.add(node);
                // 从所有节点列表中删除当前节点，避免重复遍历
                ite.remove();
            }
        }
        if (children.isEmpty()) {
            return;
        }
        parentNode.setChildren(children);
        // 递归设置子节点
        children.forEach(child -> setChildren(child, nodes));
    }
}
